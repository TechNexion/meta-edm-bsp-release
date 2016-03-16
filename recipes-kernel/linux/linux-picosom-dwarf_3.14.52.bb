# TechNexion PicoSOM i.mx6 with Dwarf baseboard

LICENSE = "GPLv2"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "tn-imx-3.14.52_1.1.0_ga"

SRC_URI = "git://github.com/TechNexion/linux.git;branch=${SRCBRANCH} \
           file://defconfig \
           file://0001-picosom-imx6-yocto-fix-imxplayer-no-picture-issue.patch \
"

SRCREV = "1e4ad0e96e3c9b3750a966b1ffa939b3d18887d8"
LOCALVERSION = "-1.1.1-picosom_dwarf"

COMPATIBLE_MACHINE = "(picosom-dwarf-imx6)"
