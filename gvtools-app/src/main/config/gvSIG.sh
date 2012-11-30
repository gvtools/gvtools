#!/bin/sh
# gvSIG.sh: startup script for gvSIG on Unix(tm) systems

# Path to an alternative Java executable used to run gvSIG.
# Uncomment the line below to use your system's default Java.
JAVA=java

# Java tuning settings.
INI_HEAP=512
MAX_HEAP=512


###########################################
# DANGER, WILL ROBINSON!                  #
# Do not change anything below this line. #
###########################################

# Jump into "bin" folder of gvSIG installation.
cd `pwd`

# Set user preferences directory
MY_CONF_DIR="$HOME/gvSIG"
if [ -n "$GVSIG_CONF_DIR" ] ; then
	MY_CONF_DIR="$GVSIG_CONF_DIR"
fi

# Create a "gvSIG" folder for this user and make sure default files get copied in there
# if not yet present or need to be updated.

if test  ! -d "$MY_CONF_DIR" ; then
	mkdir "$MY_CONF_DIR"
fi

if test  ! -d "$MY_CONF_DIR/Styles" || test  ! -d "$MY_CONF_DIR/Symbols" ; then
	mkdir "$MY_CONF_DIR/Styles"
	mkdir "$MY_CONF_DIR/Symbols"
	cp -R gvSIG/extensiones/org.gvsig.extended-symbology/default_symbology/* "$MY_CONF_DIR/"
fi

if test  ! -e "$MY_CONF_DIR/andami-config.xml" ; then
	cp defaults/andami-config.xml "$MY_CONF_DIR/"
fi

if test  ! -e "$MY_CONF_DIR/plugins-persistence.xml" ; then
	cp defaults/plugins-persistence.xml "$MY_CONF_DIR/"
fi

# user-defined spatial reference systems
if test  ! -e "$MY_CONF_DIR/usr.script" ; then
	cp defaults/usr.script "$MY_CONF_DIR/"
	cp defaults/usr.properties "$MY_CONF_DIR/"	
fi


# Setup Java environment and run gvSIG.

for i in ./lib/*.jar ; do
  LIBRARIES=$LIBRARIES:"$i"
done
for i in ./lib/*.zip ; do
  LIBRARIES=$LIBRARIES:"$i"
done

# Default is to use the bundled JRE
if [ ! -n "$JAVA" ] ; then
	echo Java must be insalled.
	exit
fi

$JAVA -DgvSIG.confDir="$MY_CONF_DIR" -DPlastic.defaultTheme=Silver -cp $LIBRARIES -Xms${INI_HEAP}M -Xmx${MAX_HEAP}M com.iver.andami.Launcher gvSIG extensions "$@"
