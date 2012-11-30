#!/bin/sh
[ -x /usr/bin/dirname ] && cd `dirname $0`
export LD_LIBRARY_PATH="$LD_LIBRARY_PATH":../../libs/
java -Djava.library.path=/usr/lib:/home/cesar/libs -cp andami.jar:lib/beans.jar:./lib/castor-0.9.5.3-xml.jar:lib/commons-dbcp-1.0-dev-20020806.zip:lib/commons-codec-1.3.jar:lib/commons-collections-3.1.zip:lib/commons-pool-1.2.zip:/lib/crimson.jar:lib/gvsig-i18n.jar:./lib/javaws.jar:lib/JWizardComponent.jar:./lib/iver-utiles.jar:./lib/log4j-1.2.8.jar:lib/looks-2.0.2.jar:lib/tempFileManager.jar:./lib/xerces_2_5_0.jar:./lib/xml-apis.jar: -Xmx500M com.iver.andami.Launcher gvSIG gvSIG/extensiones "$@"
