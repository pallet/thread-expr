# pallet.thread-expr

A library containing macros for use in clojure threading expressions (using ->).

See [reference documentation](http://pallet.github.com/thread-expr/autodoc/index.html)
and [annotated source](http://pallet.github.com/thread-expr/marginalia/uberdoc.html).

## Installation

thread-expr is distributed as a jar, and is available in the
[sonatype repository](http://oss.sonatype.org/content/repositories/releases/org/cloudhoist).

Installation is with maven or your favourite maven repository aware build tool.

### lein/cake project.clj

    :dependencies [[org.cloudhoist/thread-expr "0.1.0"]]
    :repositories {"sonatype"
                   "http://oss.sonatype.org/content/repositories/releases"}

### maven pom.xml

    <dependencies>
      <dependency>
        <groupId>org.cloudhoist</groupId>
        <artifactId>thread-expr</artifactId>
        <version>0.1.0</version>
      </dependency>
    <dependencies>

    <repositories>
      <repository>
        <id>sonatype</id>
        <url>http://oss.sonatype.org/content/repositories/releases</url>
      </repository>
    </repositories>

## License

Licensed under [EPL](http://www.eclipse.org/legal/epl-v10.html)

Copyright 2011 Hugo Duncan.
