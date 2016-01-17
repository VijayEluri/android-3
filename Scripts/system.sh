# system.sh for user create custom scripts
# =====================================================
# SRC_ROM -- cm-11-20151213-NIGHTLY-endeavoru.zip
# SRC_ROM_DIR -- cm-11-20151213-NIGHTLY-endeavoru
# SRC_ROM_BOOT_DIR -- cm-11-20151213-NIGHTLY-endeavoru-boot
# DST_ROM -- U950MK4.4.4.zip
# DST_ROM_DIR-- U950MK4.4.4
# DST_ROM_BOOT_DIR -- U950MK4.4.4-boot
echo "================ SYSTEM =================="
SRC_SYSTEM_DIR=$SRC_ROM_DIR/system
DST_SYSTEM_DIR=$DST_ROM_DIR/system

cp -v $SRC_SYSTEM_DIR/lib/{libc.so,libdl.so,libm.so,libstdc++.so} $DST_SYSTEM_DIR/lib/
cp -v $SRC_SYSTEM_DIR/bin/linker $DST_SYSTEM_DIR/bin/
cp -v $SRC_SYSTEM_DIR/lib/{libart.so,libart-compiler.so} $DST_SYSTEM_DIR/lib/
cp -v $SRC_SYSTEM_DIR/bin/{adb,dalvikvm} $DST_SYSTEM_DIR/bin/
#cp -v $SRC_SYSTEM_DIR/lib/*.so $DST_SYSTEM_DIR/lib/
#cp -v $SRC_SYSTEM_DIR/bin/* $DST_SYSTEM_DIR/bin/
#cp -v $SRC_SYSTEM_DIR/xbin/* $DST_SYSTEM_DIR/xbin/
cp -v $SRC_ROM_BOOT_DIR/ramdisk/init $DST_ROM_BOOT_DIR/
#cp -v custom_scripts/updater-script $DST_ROM_DIR/META-INF/com/google/android/

# update bootanimation.zip
cp -v $SRC_SYSTEM_DIR/media/bootanimation.zip $DST_SYSTEM_DIR/media/
# remove unused apks
rm -v $DST_SYSTEM_DIR/app/{Car.apk,CarAppActivity.apk,Carsound.apk,Carspead1.apk,Carspead.apk}
rm -v $DST_SYSTEM_DIR/app/{LenovoCompass.apk,Lenovomusic.apk,LenovoSoundRecorder.apk,LenovoTorch.apk}
rm -v $DST_SYSTEM_DIR/app/{Browser.apk,Bluetooth.apk,BluetoothExt.apk,BluetoothExt.odex,Bluetooth.odex}
rm -v $DST_SYSTEM_DIR/app/{DeskClock.apk,DeskClock.odex,DSPManager.apk,DSPManager.odex,Exchange2.apk,Exchange2.odex,Galaxy4.apk,Galaxy4.odex,Gallery2.apk,Gallery2.odex,InCallUI.apk,MagicSmokeWallpapers.apk,MagicSmokeWallpapers.odex,Mi-Pop.apk,Nfc.apk,Nfc.odex,RomServer_1.0.6_141105_1527_1000_r.apk,TelephonyProvider.apk,TelephonyProvider.odex}
rm -v $DST_SYSTEM_DIR/priv-app/{Contacts.apk,Contacts.odex,ContactsProvider.apk,ContactsProvider.odex,Dialer.apk,Dialer.odex,Mms.apk,Mms.odex,TeleService.apk,TeleService.odex}
