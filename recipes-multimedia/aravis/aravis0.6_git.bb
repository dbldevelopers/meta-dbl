SUMMARY = "Видеозахват GigE камер"
SECTION = "system"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "https://github.com/AravisProject/aravis;branch=aravis-0-6;protocol=https \
	file://imperx_c2010_reg_read_write.patch \
"

PV = "2020.04.27-0.6.4"
SRCREV = "cc1485569785ff5f3c248d74a25aec865d3c9b73"

inherit autotools pkgconfig gettext gtk-doc

DEPENDS = " \
	glib-2.0 \
	glib-2.0-native \
	intltool-native \
	libxml2 \
	gtk-doc \
	zlib \
"

RDEPENDS:${PN} = " \
	zlib \
"

S = "${WORKDIR}/git"

INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} = " \
	${libdir}/gstreamer-1.0/*.so \
	${libdir}/*.so* \
	${bindir}/arv-* \
	${datadir}/aravis-0.6/arv-fake-camera.xml \
	${datadir}/locale/* \
"

FILES:${PN}-dev = " \
	${includedir}/aravis-0.6/*.h \
	${libdir}/pkgconfig/aravis-0.6.pc \
	${libdir}/gstreamer-1.0/*.la \
"

FILES:${PN}-staticdev = "${libdir}/gstreamer-1.0/*.a"

FILES:${PN}-dbg = " \
	${libdir}/gstreamer-1.0/.debug \
	${libdir}/.debug \
	${bindir}/.debug \
"

EXTRA_OECONF = " \
	--enable-usb=no \
	--enable-gst-0.10-plugin=no \
	--enable-cpp-test=no \
	--enable-gst-plugin=no \
    --enable-gtk-doc=no \
    --enable-introspection=no \
    --enable-viewer=no \
"
