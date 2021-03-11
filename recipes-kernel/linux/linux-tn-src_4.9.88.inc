# Copyright 2017-2018 NXP
# Copyright 2019 TechNexion Ltd.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r0"
SRCBRANCH = "tn-imx_4.9.88_2.0.0_ga-test"
LOCALVERSION = "-${SRCREV}"
KERNEL_SRC = "git://github.com/TechNexion/linux.git"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
SRCREV = "5397f765d4180fa6d9c736a7635a3d42a3be03a1"

S = "${WORKDIR}/git"
