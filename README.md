spring-boot-starter-wro4j
==================================
A spring-boot-starter-wro4j library that simplifies the usage of the [wro4j](https://github.com/wro4j/wro4j) library. You only have to add this library a depedency to your spring-boot-starter-wro4j enabled project and a wro4j filter will be registered for you. This library currently depends on the wro4j-extension library which includes several frameworks to handle and process web resources. If you want to exclude some frameworks you currently have to do it yourself using your build systems way of excluding transitive dependencies.

## Usage

please add following code in your pom.xml

```xml
    <dependency>
      <groupId>org.mvnsearch.spring.boot</groupId>
      <artifactId>spring-boot-starter-wro4j</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>
    
```

### Configuration

you can change wro4j configuration in application.properties