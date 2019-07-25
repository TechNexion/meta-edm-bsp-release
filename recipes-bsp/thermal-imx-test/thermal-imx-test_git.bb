# Copyright (C) 2019 Technexion Ltd.

SUMMARY = "i.MX thermal test suite"
DESCRIPTION = "Bruning test for i.mx SOC"
SECTION = "app"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRCBRANCH = "yocto_thermal_test"
THERMAL_IMX_TEST_SRC = "git://github.com/TechNexion-customization/thermal_imx_test.git"
SRC_URI = "${THERMAL_IMX_TEST_SRC};branch=${SRCBRANCH} \
           file://thermal-imx-test.sh \
"
SRCREV = "d688aa5493d9a9724d5313f016510edc59e76eea"

S = "${WORKDIR}/git"


do_install() {
    install -d "${D}/opt/${PN}"
    install -m 755 *.sh "${D}/opt/${PN}"

    install -d ${D}${sysconfdir}/profile.d/
    install -m 0755 ${WORKDIR}/thermal-imx-test.sh ${D}${sysconfdir}/profile.d/
}

FILES_${PN} += "/opt/${PN}"
RDEPENDS_${PN} = "bash iperf3 stress-ng glmark2 memtester rsync"

FILES_${PN}-dbg += "/opt/${PN}/.debug"

COMPATIBLE_MACHINE = "(mx8)"