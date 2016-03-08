#!/bin/sh
#
# FSL Build Enviroment Setup Script
#
# Copyright (C) 2011-2013 Freescale Semiconductor
# Copyright (C) 2015 Technexion Ltd.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

CWD=`pwd`
PROGNAME="setup-environment"
exit_message ()
{
   echo "To return to this build environment later please run:"
   echo "    source setup-environment <build_dir>"

}

usage()
{
    echo -e "\nUsage: source fsl-setup-release.sh
    Optional parameters: [-b build-dir] [-e back-end] [-h]"
echo "
    * [-b build-dir]: Build directory, if unspecified script uses 'build' as output directory
    * [-e back-end]: Options are 'fb', 'dfb', 'x11, 'wayland'
    * [-h]: help
"
}


clean_up()
{

    unset CWD BUILD_DIR BACKEND FSLDISTRO
    unset fsl_setup_help fsl_setup_error fsl_setup_flag
    unset usage clean_up
    unset ARM_DIR META_FSL_BSP_RELEASE
    exit_message clean_up
}

# Patch recipes to fix bugs
patch -Np1 -r - sources/meta-fsl-bsp-release/imx/meta-sdk/conf/distro/include/fsl-imx-base.inc < sources/meta-edm-bsp-release/patches/0001-remove-preferred-provider-for-u-boot-and-kernel-to-l.patch
patch -Np1 -r - sources/meta-fsl-bsp-release/imx/meta-bsp/recipes-multimedia/gstreamer/gstreamer1.0-plugins-bad_%.bbappend < sources/meta-edm-bsp-release/patches/0001-Fix-append-bug-on-gstreamer1.0-plugins-bad-for-multi.patch
patch -Np1 -r - sources/meta-fsl-bsp-release/imx/meta-bsp/recipes-graphics/mesa/mesa-demos_%.bbappend < sources/meta-edm-bsp-release/patches/0001-Fix-append-bug-on-mesa-demos-for-multi-platform.patch
patch -Np1 -r - sources/poky/meta/recipes-connectivity/wpa-supplicant/wpa-supplicant.inc < sources/meta-edm-bsp-release/patches/0001-Revert-wpa-supplicant-Make-SystemD-D-Bus-config-cond.patch

# get command line options
OLD_OPTIND=$OPTIND
unset FSLDISTRO

while getopts "k:r:t:b:e:gh" fsl_setup_flag
do
    case $fsl_setup_flag in
        b) BUILD_DIR="$OPTARG";
           echo -e "\n Build directory is " $BUILD_DIR
           ;;
        e)
            # Determine what distro needs to be used.
            BACKEND="$OPTARG"
            if [ "$BACKEND" = "fb" ]; then
                if [ -z "$DISTRO" ]; then
                    FSLDISTRO='fsl-imx-release-fb'
                    echo -e "\n Using FB backend with FB DIST_FEATURES to override poky X11 DIST FEATURES"
                elif [ ! "$DISTRO" = "fsl-imx-release-fb" ]; then
                    echo -e "\n DISTRO specified conflicts with -e. Please use just one or the other."
                    fsl_setup_error='true'
                fi

            elif [ "$BACKEND" = "dfb" ]; then
                if [ -z "$DISTRO" ]; then
                    FSLDISTRO='fsl-imx-release-dfb'
                    echo -e "\n Using DirectFB backend with DirectFB DIST_FEATURES to override poky X11 DIST FEATURES"
                elif [ ! "$DISTRO" = "fsl-imx-release-dfb" ]; then
                    echo -e "\n DISTRO specified conflicts with -e. Please use just one or the other."
                    fsl_setup_error='true'
                fi

            elif [ "$BACKEND" = "wayland" ]; then
                if [ -z "$DISTRO" ]; then
                    FSLDISTRO='fsl-imx-release-wayland'
                    echo -e "\n Using Wayland backend."
                elif [ ! "$DISTRO" = "fsl-imx-release-wayland" ]; then
                    echo -e "\n DISTRO specified conflicts with -e. Please use just one or the other."
                    fsl_setup_error='true'
                fi

            elif [ "$BACKEND" = "x11" ]; then
                if [ -z "$DISTRO" ]; then
                    FSLDISTRO='fsl-imx-release-x11'
                    echo -e  "\n Using X11 backend with poky DIST_FEATURES"
                elif [ ! "$DISTRO" = "fsl-imx-release-x11" ]; then
                    echo -e "\n DISTRO specified conflicts with -e. Please use just one or the other."
                    fsl_setup_error='true'
                fi

            else
                echo -e "\n Invalid backend specified with -e.  Use fb, dfb, wayland, or x11"
                fsl_setup_error='true'
            fi
           ;;
        h) fsl_setup_help='true';
           ;;
        ?) fsl_setup_error='true';
           ;;
    esac
done


if [ -z "$DISTRO" ]; then
    if [ -z "$FSLDISTRO" ]; then
        FSLDISTRO='fsl-imx-release-x11'
    fi
else
    FSLDISTRO="$DISTRO"
fi

OPTIND=$OLD_OPTIND

# check the "-h" and other not supported options
if test $fsl_setup_error || test $fsl_setup_help; then
    usage && clean_up && return 1
fi

if [ -z "$BUILD_DIR" ]; then
    BUILD_DIR='build'
fi

if [ -z "$MACHINE" ]; then
    echo setting to default machine
    MACHINE='edm-fairy-imx6'
fi

# New machine definitions may need to be added to the expected location
cp -r sources/meta-fsl-bsp-release/imx/meta-bsp/conf/machine/* sources/meta-fsl-arm/conf/machine
cp -r sources/meta-edm-bsp-release/conf/machine/* sources/meta-fsl-arm/conf/machine

# copy new EULA into community so setup uses latest i.MX EULA
cp sources/meta-fsl-bsp-release/imx/EULA.txt sources/meta-fsl-arm/EULA
# copy unpack class with md5sum that matches new EULA
cp sources/meta-fsl-bsp-release/imx/classes/fsl-eula-unpack.bbclass sources/meta-fsl-arm/classes

# Set up the basic yocto environment
if [ -z "$DISTRO" ]; then
   DISTRO=$FSLDISTRO MACHINE=$MACHINE . ./$PROGNAME $BUILD_DIR
else
   MACHINE=$MACHINE . ./$PROGNAME $BUILD_DIR
fi

# Point to the current directory since the last command changed the directory to $BUILD_DIR
BUILD_DIR=.

if [ ! -e $BUILD_DIR/conf/local.conf ]; then
    echo -e "\n ERROR - No build directory is set yet. Run the 'setup-environment' script before running this script to create " $BUILD_DIR
    echo -e "\n"
    return 1
fi

# On the first script run, backup the local.conf file
# Consecutive runs, it restores the backup and changes are appended on this one.
if [ ! -e $BUILD_DIR/conf/local.conf.org ]; then
    cp $BUILD_DIR/conf/local.conf $BUILD_DIR/conf/local.conf.org
else
    cp $BUILD_DIR/conf/local.conf.org $BUILD_DIR/conf/local.conf
fi


if [ ! -e $BUILD_DIR/conf/bblayers.conf.org ]; then
    cp $BUILD_DIR/conf/bblayers.conf $BUILD_DIR/conf/bblayers.conf.org
else
    cp $BUILD_DIR/conf/bblayers.conf.org $BUILD_DIR/conf/bblayers.conf
fi


META_FSL_BSP_RELEASE="${CWD}/sources/meta-fsl-bsp-release/imx/meta-bsp"
echo "##Freescale Yocto Release layer" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-fsl-bsp-release/imx/meta-bsp \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-fsl-bsp-release/imx/meta-sdk \"" >> $BUILD_DIR/conf/bblayers.conf

echo "BBLAYERS += \" \${BSPDIR}/sources/meta-browser \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-openembedded/meta-gnome \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-openembedded/meta-networking \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-openembedded/meta-python \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-openembedded/meta-ruby \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-openembedded/meta-filesystems \"" >> $BUILD_DIR/conf/bblayers.conf

echo "BBLAYERS += \" \${BSPDIR}/sources/meta-qt5 \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-edm-bsp-release \"" >> $BUILD_DIR/conf/bblayers.conf

if [ "$MACHINE" == "edm-goblin-imx6sx" ] ; then
	echo "setting to default display lvds7"
	DISPLAY="lvds7"
elif [ "$DISPLAY" != "lvds7" ] && [ "$DISPLAY" != "hdmi720p" ] && [ "$DISPLAY" != "hdmi1080p" ]  \
&& [ "$DISPLAY" != "lcd" ] && [ "$DISPLAY" != "lvds7_hdmi720p" ] && [ "$DISPLAY" != "custom" ] ; then
	echo "Display is wrong. Please assign DISPLAY as one of lvds7, hdmi720p, hdmi1080p, lcd, lvds7_hdmi720p, lcd, custom"
	echo "setting to default display hdmi720p"
	DISPLAY="hdmi720p"
fi

echo "LICENSE_FLAGS_WHITELIST=\"commercial\"" >> $BUILD_DIR/conf/local.conf

echo "display type is $DISPLAY"
echo "DISPLAY_TYPE = \"$DISPLAY\"" >> $BUILD_DIR/conf/local.conf
unset DISPLAY

cd  $BUILD_DIR
clean_up
unset FSLDISTRO
