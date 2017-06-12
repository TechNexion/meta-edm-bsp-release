# For TechNexion boards

LICENSE = "GPLv2"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "tn-imx_4.1.15_2.0.0_ga"

SRC_URI = "git://github.com/TechNexion/linux.git;branch=${SRCBRANCH} \
           file://defconfig \
"

SRCREV = "ebd66b6b051249e9bf256bee0227f8f9c0b7f115"
LOCALVERSION = "-2.0.0-technexion"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"

