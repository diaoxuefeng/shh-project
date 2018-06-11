package com.diao.base.config;

import org.apache.commons.lang.StringUtils;
import com.google.common.collect.Sets;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Set;

public class WildcardSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private static final Logger log = LoggerFactory.getLogger(WildcardSqlSessionFactoryBean.class);
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    public WildcardSqlSessionFactoryBean() {
    }
    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        typeAliasesPackage = "classpath*:" + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/" + "**/*.class";

        try {
            Set<String> result = Sets.newHashSet();
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if (resources != null && resources.length > 0) {
                MetadataReader metadataReader = null;
                Resource[] var7 = resources;
                int var8 = resources.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    Resource resource = var7[var9];
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);

                        try {
                            result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                        } catch (ClassNotFoundException var12) {
                            ;
                        }
                    }
                }
            }

            if (result.size() > 0) {
                super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
            } else {
                log.warn("参数typeAliasesPackage:" + typeAliasesPackage + "，未找到任何包");
            }
        } catch (IOException var13) {
            log.error("解析typeAliasesPackage失败!", var13);
        }

    }
}
