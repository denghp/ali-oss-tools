package org.mvnsearch.ali.oss.spring.services;

import org.jetbrains.annotations.Nullable;

/**
 * config service
 *
 * @author denghp
 */
public interface ConfigService {

    /**
     * available info
     *
     * @return available infor
     */
    public boolean available();

    /**
     * set access info
     *
     * @param accessId  access id
     * @param accessKey access key
     */
    public void setAccessInfo(String accessId, String accessKey);

    /**
     * set local file repository
     *
     * @param repository repository
     */
    public void setRepository(String repository);

    /**
     * get local repository
     *
     * @return local repository
     */
    public String getRepository();

    /**
     * get configuration
     *
     * @param key key
     * @return value
     */
    String getProperty(String key);

    /**
     * update config
     *
     * @param key   key
     * @param value value
     */
    void setProperty(String key, @Nullable String value);
}
