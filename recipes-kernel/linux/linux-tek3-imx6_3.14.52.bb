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

SRCREV = "1e4ad0e96e3c9b3750a966b1ffa939b3d18887d8"
LOCALVERSION = "-1.1.1-tek3_imx6"


COMPATIBLE_MACHINE = "(tek3-imx6)"

