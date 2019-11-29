# For TechNexion boards

LICENSE = "GPLv2"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

TNLINUX_GITHUB_MIRROR ?= "git://github.com/TechNexion/linux.git"

SRCBRANCH = "tn-imx_4.1.15_2.0.0_ga"

SRC_URI = "${TNLINUX_GITHUB_MIRROR};branch=${SRCBRANCH} \
           file://defconfig \
"

SRCREV = "06465e7a7ee6fd3d123ea14e706b8afe401868e2"
LOCALVERSION = "-2.0.0-technexion"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"

