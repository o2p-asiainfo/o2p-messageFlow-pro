package com.ailk.o2p.messageFlow.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.ailk.o2p.messageFlow.cache.CacheFactory;
import com.ailk.o2p.messageFlow.cache.ICache;
import com.ailk.o2p.messageFlow.utils.PropertiesLoader;
import com.ailk.o2p.messageFlow.utils.SystemKeyWords;
import com.asiainfo.foundation.log.Logger;

public class I18nListener implements ServletContextListener {

	private static final Logger log = Logger.getLog(I18nListener.class);

	private PropertiesLoader i18nLoader = null;

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent sc) {
		i18nLoader = new PropertiesLoader("local/local.properties");
		String localResource = i18nLoader.getProperty("spring.i18n.resources");
		String locale = i18nLoader.getProperty("spring.locale");
		String[] localResourceAry = StringUtils.split(localResource, ",");
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		List<String> resourcesPathList = new ArrayList<String>();
		for (String localFolder : localResourceAry) {
			try {
				String pathToUse = resourceLoader.getResource(localFolder)
						.getURL().getPath();
				File dir = new File(pathToUse);
				File[] files = dir.listFiles();
				for (File f : files) {
					if (!f.isDirectory()) {
						String fName = f.getName();
						String location = localFolder + "/" + fName;
						if (location.endsWith("_" + locale + ".properties")) {
							resourcesPathList.add(location);
							log.info(location);
						}
					}
				}
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		}
		resourcesPathList.add("local/local.properties");
		String[] resourcesPaths = new String[resourcesPathList.size()];
		resourcesPathList.toArray(resourcesPaths);
		i18nLoader = new PropertiesLoader(resourcesPaths);
		ICache cache = CacheFactory
				.newCacheInstance(CacheFactory.CACHE_MODEL_EHCACHE);
		cache.set(SystemKeyWords.I18N_LOADER_NAME, i18nLoader);
		i18nLoader = null;
	}

}
