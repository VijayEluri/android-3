#!/bin/bash -

SRC_ROM=cm-11-20151213-NIGHTLY-endeavoru.zip 
DST_ROM=U950MK4.4.4.zip

# checking roms
if [ ! -f "dl/$SRC_ROM" ] ; then
	echo "Can not find the rom dl/$SRC_ROM!" 
	exit 1
fi

if [ ! -f "dl/$DST_ROM" ] ; then
	echo "Can not find the rom dl/$DST_ROM!" 
	exit 1
fi

# checking rom extract dir
SRC_ROM_DIR=${SRC_ROM%.zip}
DST_ROM_DIR=${DST_ROM%.zip}

if [ ! -x $SRC_ROM_DIR ] ; then
	mkdir $SRC_ROM_DIR
	(cd $SRC_ROM_DIR && unzip ../dl/$SRC_ROM)
fi

# the dst rom always changed
rm -rf $DST_ROM_DIR
if [ ! -x $DST_ROM_DIR ] ; then
	mkdir $DST_ROM_DIR
	(cd $DST_ROM_DIR && unzip ../dl/$DST_ROM)
fi

# extract boot.img
SRC_ROM_BOOT_DIR=${SRC_ROM%.zip}-boot
DST_ROM_BOOT_DIR=${DST_ROM%.zip}-boot

rm -rf $SRC_ROM_BOOT_DIR
rm -rf $DST_ROM_BOOT_DIR
mkdir $SRC_ROM_BOOT_DIR
mkdir $DST_ROM_BOOT_DIR

unpackbootimg -i $SRC_ROM_DIR/boot.img -o $SRC_ROM_BOOT_DIR
unpackbootimg -i $DST_ROM_DIR/boot.img -o $DST_ROM_BOOT_DIR

# extract ramdisk
(cd $SRC_ROM_BOOT_DIR; mkdir ramdisk; cd ramdisk; gzip -dc ../boot.img-ramdisk.gz | cpio -i)
(cd $DST_ROM_BOOT_DIR; mkdir ramdisk; cd ramdisk; gzip -dc ../boot.img-ramdisk.gz | cpio -i)

# export global VARs for user
export SRC_ROM DST_ROM SRC_ROM_DIR DST_ROM_DIR SRC_ROM_BOOT_DIR DST_ROM_BOOT_DIR
CUSTOM_SCRIPTS_DIR=custom_scripts
# next, we should leave hooks for user
if [ ! -x $CUSTOM_SCRIPTS_DIR ] ; then
	mkdir -v $CUSTOM_SCRIPTS_DIR
	echo "# demo.sh for user create custom scripts" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# =====================================================" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# SRC_ROM -- $SRC_ROM" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# SRC_ROM_DIR -- $SRC_ROM_DIR" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# SRC_ROM_BOOT_DIR -- $SRC_ROM_BOOT_DIR" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# DST_ROM -- $DST_ROM" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# DST_ROM_DIR-- $DST_ROM_DIR" >> $CUSTOM_SCRIPTS_DIR/demo.sh
	echo "# DST_ROM_BOOT_DIR -- $DST_ROM_BOOT_DIR" >> $CUSTOM_SCRIPTS_DIR/demo.sh

fi

echo "Enter into user hooks..."
for USER in custom_scripts/*.sh
do
	source $USER
done
echo "complete!"

# begin make sign packages
(cd $DST_ROM_BOOT_DIR; mkbootfs ramdisk > boot.img-ramdisk.gz; mkbootimg --kernel boot.img-zImage --ramdisk boot.img-ramdisk.gz --second boot.img-second --cmdline "" --board "Android boot" --base 0x10000000 --pagesize 2048 --dt boot.img-dt --ramdisk_offset 0x01000000 --second_offset 0x00f00000 --tags_offset 0x00000100 -o boot.img)

cp -v $DST_ROM_BOOT_DIR/boot.img $DST_ROM_DIR/boot.img

DST_ROM_UPDATE_DIR=${DST_ROM%.zip}-update
DST_ROM_UPDATE_SIGN=${DST_ROM%.zip}-signed.zip
DST_ROM_UPDATE_UNSIGN=${DST_ROM%.zip}-unsigned.zip
mkdir -p $DST_ROM_UPDATE_DIR
(cd $DST_ROM_DIR; zip -r9 ../$DST_ROM_UPDATE_DIR/$DST_ROM_UPDATE_UNSIGN *)
(cd $DST_ROM_UPDATE_DIR; testsign.sh $DST_ROM_UPDATE_UNSIGN $DST_ROM_UPDATE_SIGN)
echo "complete!"
