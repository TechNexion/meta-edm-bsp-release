# TechNexion TEK3-IMX6

LICENSE = "GPLv2"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "tn-imx-3.14.52_1.1.0_ga"

SRC_URI = "git://github.com/TechNexion/linux.git;branch=${SRCBRANCH} \
           file://defconfig \
           file://0001-tek3-imx6-yocto-fix-imxplayer-no-picture-issue.patch \
"

SRCREV = "6162fd1ac8f7e02c4c47ab9783a53bf7143d60b4"
LOCALVERSION = "-1.1.1-tek3_imx6"


COMPATIBLE_MACHINE = "(tek3-imx6)"

