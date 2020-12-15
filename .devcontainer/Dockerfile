# Use ONLY for local developing. 
# Do not user docker image with user "root" user in production env !

FROM openjdk:11.0.8-jdk

RUN apt-get update
RUN apt-get install -y \
    wget \
    libfontconfig \
    unzip \
    zip \
    ksh \
    curl

# [Option] Install Maven
ARG INSTALL_MAVEN="true"
ARG MAVEN_VERSION="3.6.3"

## Install maven
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then /bin/bash -c "wget -q http://mirror.cogentco.com/pub/apache/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && tar xzvf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && rm apache-maven-${MAVEN_VERSION}-bin.tar.gz && ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn && mvn --version"; fi
COPY .m2/repository/ /root/.m2/repository/

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>
