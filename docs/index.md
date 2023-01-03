dd-virus-scan
===========================

Service for scanning Dataverse datasets for virus

SYNOPSIS
--------

    dd-virus-scan { server | check }

DESCRIPTION
-----------

Service for scanning Dataverse datasets for virus. The service uses the `clamd` daemon for the actual scanning. The data files of the dataset
will be streamed through the `clamd` deamon one by one. If no virus is found the resume status will be _"Success"_, otherwise _"Failure"_ with in the message
the instantiated `resultPostiveMessageTemplate`. See the comments in the `config.yml` for details.

The service has the following thread pools:

* Workers for incoming HTTP requests from Dataverse. These will schedule scan tasks.
* Scan task workers. These will loop over all the files in the targeted dataset and stream them through `scand`, gathering the results. The overall result will
  be determined a resumption of the workflow will be scheduled as a resume task.
* Resume task workers. These will try to resume the workflow. This will be tried a configured number of times because Dataverse has known synchronization issues
  in its workflow framework.

### References

* https://linux.die.net/man/8/clamd

ARGUMENTS
---------

        positional arguments:
        {server,check}         available commands
        
        named arguments:
        -h, --help             show this help message and exit
        -v, --version          show the application version and exit

INSTALLATION AND CONFIGURATION
------------------------------
Currently this project is built as an RPM package for RHEL7/CentOS7 and later. The RPM will install the binaries to
`/opt/dans.knaw.nl/dd-virus-scan` and the configuration files to `/etc/opt/dans.knaw.nl/dd-virus-scan`.

For installation on systems that do no support RPM and/or systemd:

1. Build the tarball (see next section).
2. Extract it to some location on your system, for example `/opt/dans.knaw.nl/dd-virus-scan`.
3. Start the service with the following command
   ```
   /opt/dans.knaw.nl/dd-virus-scan/bin/dd-virus-scan server /opt/dans.knaw.nl/dd-virus-scan/cfg/config.yml 
   ```

BUILDING FROM SOURCE
--------------------
Prerequisites:

* Java 11 or higher
* Maven 3.3.3 or higher
* RPM

Steps:

    git clone https://github.com/DANS-KNAW/dd-virus-scan.git
    cd dd-virus-scan 
    mvn clean install

If the `rpm` executable is found at `/usr/local/bin/rpm`, the build profile that includes the RPM packaging will be activated. If `rpm` is available, but at a
different path, then activate it by using Maven's `-P` switch: `mvn -Pprm install`.

Alternatively, to build the tarball execute:

    mvn clean install assembly:single
