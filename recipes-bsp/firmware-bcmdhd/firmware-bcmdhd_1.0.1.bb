SUMMARY = "Broadcom Wi-fi & bluetooth firmware"
DESCRIPTION = "Broadcom Wi-fi & bluetooth firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README;md5=6cb3769c7bbeea6c6cbdd5aa2f32f658"

SRC_URI += " \
    file://README \
    file://fw_bcm4330_bg.bin \
    file://fw_bcm4330_apsta_bg.bin \
    file://brcmfmac4330-sdio.txt \
    file://bcm4330.hcd \
    file://fw_bcm4339a0_ag.bin \
    file://fw_bcm4339a0_ag_apsta.bin \
    file://nvram_ap6335.txt \
    file://bcm4339a0.hcd \
    file://fw_bcm43438a0.bin \
    file://fw_bcm43438a0_apsta.bin \
    file://nvram_ap6212.txt \
    file://bcm43438a0.hcd \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/lib/firmware/brcm
    
    #Install BCM4330 wifi and bluetooth firmware
    install -m 0755 fw_bcm4330_bg.bin ${D}/lib/firmware/brcm
    install -m 0755 fw_bcm4330_apsta_bg.bin ${D}/lib/firmware/brcm
    install -m 0755 brcmfmac4330-sdio.txt ${D}/lib/firmware/brcm
    install -m 0755 bcm4330.hcd ${D}/lib/firmware/brcm
    
    #Install AP6335 wifi and bluetooth firmware
    install -m 0755 fw_bcm4339a0_ag.bin ${D}/lib/firmware/brcm
    install -m 0755 fw_bcm4339a0_ag_apsta.bin ${D}/lib/firmware/brcm
    install -m 0755 nvram_ap6335.txt ${D}/lib/firmware/brcm
    install -m 0755 bcm4339a0.hcd ${D}/lib/firmware/brcm

    #Install AP6212 wifi and bluetooth firmware
    install -m 0755 fw_bcm43438a0.bin ${D}/lib/firmware/brcm
    install -m 0755 fw_bcm43438a0_apsta.bin ${D}/lib/firmware/brcm
    install -m 0755 nvram_ap6212.txt ${D}/lib/firmware/brcm
    install -m 0755 bcm43438a0.hcd ${D}/lib/firmware/brcm
}

FILES_${PN}-dbg += "/lib/firmware/.debug"
FILES_${PN} += "/lib/firmware/"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
