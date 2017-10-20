SUMMARY = "Broadcom Wi-fi & bluetooth firmware"
DESCRIPTION = "Broadcom Wi-fi & bluetooth firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README;md5=008f5f07af5434cb1d774fcc0e0381a7"

SRC_URI += " \
    file://README \
    file://fw_bcm4330_bg.bin \
    file://fw_bcm4330_apsta_bg.bin \
    file://brcmfmac4330-sdio.txt \
    file://bcm4330.hcd \
    file://bcm4330b1.hcd \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/lib/firmware/brcm
    
    #Install BCM4330 wifi and bluetooth firmware
    install -m 0755 fw_bcm4330_bg.bin ${D}/lib/firmware/brcm
    install -m 0755 fw_bcm4330_apsta_bg.bin ${D}/lib/firmware/brcm
    install -m 0755 brcmfmac4330-sdio.txt ${D}/lib/firmware/brcm
    install -m 0755 bcm4330.hcd ${D}/lib/firmware/brcm
    install -m 0755 bcm4330b1.hcd ${D}/lib/firmware/brcm
}

FILES_${PN}-dbg += "/lib/firmware/.debug"
FILES_${PN} += "/lib/firmware/"

COMPATIBLE_MACHINE = "(mx6)"
