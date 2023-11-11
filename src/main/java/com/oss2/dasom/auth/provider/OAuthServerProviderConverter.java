package com.oss2.dasom.auth.provider;

import org.springframework.core.convert.converter.Converter;

public class OAuthServerProviderConverter implements Converter<String, OAuthServerProvider> {

    @Override
    public OAuthServerProvider convert(String source) {
        return OAuthServerProvider.fromName(source);
    }
}