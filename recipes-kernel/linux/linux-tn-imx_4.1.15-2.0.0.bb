# For TechNexion boards

LICENSE = "GPLv2"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "tn-imx_4.1.15_2.0.0_ga"

SRC_URI = "git://github.com/TechNexion/linux.git;branch=${SRCBRANCH} \
           file://defconfig \
"

SRCREV = "6e8e41bdef83ddaee77a39c484cbe12a49281f60"
LOCALVERSION = "-2.0.0-technexion"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"

