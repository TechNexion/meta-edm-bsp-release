# Copyright 2017-2019 TN
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Detemine Correct TechNexion's WIFI firmware and drivers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup


RDEPENDS_${PN} = " \
    openssh-sftp-server \
    libsocketcan \
    bash \
    hostapd \
    dnsmasq \
    haveged \
    create-ap \
    iptables \
    rsync \
    picocom \
    dtc \
    libgpiod \
"

