#!/bin/bash
#
ROOT_PATH=$PWD
BUILD_PATH="$ROOT_PATH/build/outputs/apk/quickstepLawnchairCrdroid/release"
APP_NAME="crDroidHome"

# Build app
./gradlew assembleQuickstepLawnchairCrdroidRelease --info

#sign and zipalign
java -jar sign.jar $BUILD_PATH/Lawnchair-quickstep-lawnchair-crdroid-release-unsigned.apk
mv $BUILD_PATH/Lawnchair-quickstep-lawnchair-crdroid-release-unsigned.apk $ROOT_PATH/$APP_NAME.apk
mv $BUILD_PATH/Lawnchair-quickstep-lawnchair-crdroid-release-unsigned.s.apk $ROOT_PATH/$APP_NAME-signed.apk

# cleanup
#./gradlew clean
