package com.ailk.o2p.messageFlow.utils;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.ailk.eaap.o2p.common.spring.config.EncryptPropertyPlaceholderConfigurer;

public class LocalePropertyPlaceholderConfigurer extends
		EncryptPropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		CommonConfigurations.addProperties(props);
	}

}
