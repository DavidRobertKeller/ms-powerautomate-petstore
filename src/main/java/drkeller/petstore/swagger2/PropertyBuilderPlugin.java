package drkeller.petstore.swagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import springfox.bean.validators.plugins.Validators;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@Component
@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class PropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

	@Override
	public boolean supports(DocumentationType delimiter) {
		return SwaggerPluginSupport.pluginDoesApply(delimiter);
	}
	
	public void apply(final ModelPropertyContext context) {
		final ModelPropertyBuilder builder = context.getBuilder();

		final Optional<BeanPropertyDefinition> beanPropDef = context.getBeanPropertyDefinition();

		if (!beanPropDef.isPresent()) {
			return;
		}

		final BeanPropertyDefinition beanDef = beanPropDef.get();
		final AnnotatedMethod method = beanDef.getGetter();
		if (method == null) {
			return;
		}
		
		final ApiModelProperty apiModelProperty = method.getAnnotation(ApiModelProperty.class);

		if (apiModelProperty == null) {
			return;
		}

		List<VendorExtension> vendorExtensions = new ArrayList<>();

		/**
		 * @see https://github.com/springfox/springfox/issues/2739#issuecomment-836573641 
		 */
		if (apiModelProperty.extensions().length > 0) {
			Extension[] extensionAnnotations = apiModelProperty.extensions();
			for (Extension extensionAnnotation : extensionAnnotations) {
				for (ExtensionProperty prop : extensionAnnotation.properties()) {
//					vendorExtensions.add(new StringVendorExtension("x-"+prop.name(), prop.value()));
					if (prop.value().startsWith("boolean:")) {
						vendorExtensions.add(new BooleanVendorExtension("x-"+prop.name(), prop.value().substring("boolean:".length())));
					} else {
						vendorExtensions.add(new StringVendorExtension("x-"+prop.name(), prop.value()));
					}
					
				}
			}
		}

		context.getSpecificationBuilder().vendorExtensions(vendorExtensions);
//		builder.extensions(vendorExtensions);
	}
}
