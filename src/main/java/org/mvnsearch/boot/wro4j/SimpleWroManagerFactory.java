package org.mvnsearch.boot.wro4j;

import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimpleWroManagerFactory extends ConfigurableWroManagerFactory {
    private final Properties configProperties;

    public SimpleWroManagerFactory(Properties configProperties) {
        this.configProperties = configProperties;
    }

    @Override
    protected Properties newConfigProperties() {
        return configProperties;
    }

    @Override
    protected WroModelFactory newModelFactory() {
        return new XmlModelFactory() {
            @Override
            protected InputStream getModelResourceAsStream() throws IOException {
                String resourceLocation = "/wro.xml";
                final InputStream stream = getClass().getResourceAsStream(resourceLocation);
                if (stream == null) {
                    throw new IOException("Invalid resource requested: "
                            + resourceLocation);
                }
                return stream;
            }
        };
    }

}
