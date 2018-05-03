# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-imx-src.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

WANDBOARD_GITHUB_MIRROR ?= "git://github.com/wandboard-org/linux.git"

SRCBRANCH = "wand-pi-8m_imx_4.9.51_imx8m_ga_test"

SRC_URI = "${WANDBOARD_GITHUB_MIRROR};branch=${SRCBRANCH}"

SRCREV = "7f45dbee0187c340ee3f31c38fc82078e770faed"
LOCALVERSION = "-${SRCBRANCH}"


addtask copy_defconfig after do_unpack before do_preconfigure
do_copy_defconfig () {
    install -d ${B}
    # copy latest defconfig to use for mx8
    mkdir -p ${B}
    cp ${S}/arch/arm64/configs/wand_pi_defconfig ${B}/.config
    cp ${S}/arch/arm64/configs/wand_pi_defconfig ${B}/../defconfig
}

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
EXTRA_OEMAKE_append_mx6 = " ARCH=arm"
EXTRA_OEMAKE_append_mx7 = " ARCH=arm"
EXTRA_OEMAKE_append_mx8 = " ARCH=arm64"
