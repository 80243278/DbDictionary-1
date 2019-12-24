package com.yunhesoft.tm4.dbdictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @category Api文档配置文件
 * 		 swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
 * 
 *  <pre>
 *  注意：此配置文件是为统一tm4的api文档所建立
 *           针对对外公开的文档，可以通过编写相应的代码来实现自动生成实时的Api文档
 *           避免文档滞后的问题
 *  要求：每个开发者在编写各自的模块时，均需引入此配置文件，并修改自己相应的配置信息
 *     为测试人员，以及前端开发人员提供更详细的Api文档说明
 *  使用：localhost:xxxx/swagger-ui.html
 *     xxxx为模块的端口号
 *         更多使用方法请参见swagger2文档
 *  注释格式：参见核心包的controller的示例
 *  </pre>
 * 
 * @author 
 *
 */
@Configuration
@EnableSwagger2
public class ApiDocConfigSwagger2 {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 为当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.yunhesoft.tm4.dbdictionary")).paths(PathSelectors.any())
				.build();
	}

	/**
	 * @category 构建 api文档的详细信息函数，注意这里的注解引用的是哪个
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("DbDictionary")
				// 创建人
				// .contact("zhang.jt")
				// 版本号
				.version("0.1")
				// 描述
				.description("com.yunhesoft.tm4.dbdictionary API描述").build();
	}

}
