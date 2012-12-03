Developer guide
================

Running gvtools from sources 
------------------------------

gvSIG architecture
...................

gvSIG is a set of plugins running on a plugin framework called "andami" (_fwAndami). 

Essentially, a plugin in gvSIG is a folder containing:

 * a *config.xml* file with the configuration of the plugin
 * jar files containing the compiled Java code 

The config.xml file defines one or more extensions to the user interface that 
are managed by a Java class. Thus, the following extension declaration::

	<extension class-name="org.gvsig.TestExtension" active="true">
		<menu text="Test/test" action-command="" position="-100" />
	</extension>

will install a menu entry called "test" inside a menu called "Test" and each
time the user will click the menu, the class TestExtension will be called.	   

Two repositories
..................

Gvtools code can be found in GitHub: `<https://github.com/gvtools/gvtools>`_.

The gvSIG code base that we're using as reference can be found also in GitHub: `<https://github.com/gvtools/gvtools-legacy>`_. 
This code base can be configured with *mvn eclipse:eclipse* so that it is possible to install **some** of the projects in eclipse
and navigate through the classes.

One of the aims we have is to be able to build gvtools with a single maven command. This is no longer possible,
nor necessary, on the current gvSIG codebase so that's why we prefer to keep one repository with
gvSIG codebase, just to clone and import in Eclipse, and another repository with a new structure that can be
easily built.

See `Migration methodology`_ for more information about why this is necessary and sufficient.  

Project structure
..................

The gvtools project consists of the following projects:

- andami: The plug-in system
- base-plugin: A core andami plug-in that manages other plug-in windows. Crap. Do not touch.
- main: The only plug-in (appart of core-plugin) of gvtools. Instead of having a lot of different
  plugin projects we only have one for now. There is no need to split the project
  so far.
- core: The library that contains all the code that can be implemented with
  Geotools

Workspace configuration
.........................

Clone the repository::

	$ git clone git@github.com:gvtools/gvtools.git
	$ cd gvtools

Compile::

	$ mvn compile
	
Or if you're working with eclipse::

	$ mvn eclipse:eclipse

And then import the existing project in eclipse. Sorry no idea about maven
plugin for eclipse, I love eclipse plugin for maven, this is, I love
doing it command-line style.

Running
........

Package the application::

	$ mvn package

This will package the artifacts separately in their *target* folders. 
To create a ready to run build, use the *build* profile::

	$ mvn package -Pbuild

If there are test failures and you really want to package the application, 
use the skipTests property::

	$ mvn package -Pbuild -DskipTests

And run the application::

	$ cd build/gvtools-<version>
	$ ./gvSIG.sh

Sorry, the Windows version is not available yet.

In order to run/debug the application using Eclipse, a new Run (or Debug)
configuration must be created. Use the *Run -> Run configurations...* (or
*Debug -> Debug configurations...*) menu, and double-click on 
*Java Application*.

On the *Main* tab set the *Project* to *andami* and the *Main class* to 
*com.iver.core.Launcher*:

.. image:: images/run_main.png
   :align: center

Then switch to the *Arguments* tab and set the *Program arguments* to
*gvtools extensions* and the *Working directory* to the build directory
(*build/gvtools-<version>*):

.. image:: images/run_args.png
   :align: center
Run.

Contributing
-------------

The following list contains some of the ways to contribute to gvtools:

- Migrate user interface functionalities from gvSIG into *main*
- Tests for the migrated user interface code. It really needs it but it's hard. Refactorings will very probably be necessary
- Improvements to the *core* library
- More tests to *core* library

Any help is welcome and everything can be discussed in the mailing list.

.. _methodology:

Migration methodology
----------------------

In order to migrate user interface functionalities from gvtools-legacy to gvtools, it is advisable to
follow this methodology.

What we're trying with this is to do the minimum effort to make gvSIG user interface work
with Geotools. So, in order to add any code to *core*, the code must be required by some
user interface functionality copied from the gvtools-legacy.

Schematically:

* Copy all extensions to *main* one by one. For each extension:

  * Fix all compilation errors by several ways:
	
    * If it is something that can be done by core/Geotools, implement it with core/Geotools. This
      may require modifying *core* so don't hesitate to push some code, propose patches on the list
      or to discuss them previously if the change is too big. Javadoc and unit tests are mandatory.
	
    * If it is a missing user interface class, copy it. Please, maintain the package so that we can compare in the future.
		
    * Remove the code. It is allowed to remove code if:
		
      * It supports old 0.3 version (method name ends with '03')

      * Public methods that are not called in main -> Either they will be called with
        code that will be moved to main and we'll recover the erased method, or
        they will never be called so we were right to remove it.

      * Whole classes -> At the end we can compare old and new codebase and we can detect the classes that are missing.
        
      * In general, any code that it is not necessary right now and we'll be forced to add it later.

Some considerations when following the method:

* Our first priority is integrating Geotools. Please don't try to fix the extension code in
  *main*. While following the previous method you will see some things that can easily be fixed.
  Please don't. You'll realize that there are many of these and fixing them will keep you from
  the real aim, which is the integration. Let's try to do only minimal changes to the code
  that goes to main.

* Do not add TODOs. They are ignored. Assert false:"todo message"; is much more effective. If
  you don't want to implement a method, try to remove it (following the rules stated before).

* If you add some comment related to the integration, include the text "gtintegration" in
  it, so that we can know it is related to gvtools.

* Please, try to understand core before making changes. Just use the mailing list to agree
  on some changes before starting to code, or be prone to refactor *a posteriori*. 
