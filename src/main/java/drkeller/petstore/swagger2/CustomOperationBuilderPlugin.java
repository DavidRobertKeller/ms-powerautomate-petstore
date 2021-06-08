package drkeller.petstore.swagger2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import drkeller.petstore.dto.PetWebhookResponse;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class CustomOperationBuilderPlugin implements OperationBuilderPlugin {

	@Override
	public void apply(OperationContext context) {

		/**
		 * for createHook, add this vendor parameter
		 * 
	      	x-ms-notification-content:
		        description: Details for Webhook
		        schema: '{"$ref": "#/definitions/WebhookPushResponse"}'
		 */
		
		if ("createHook".equals(context.getName())) {
			@SuppressWarnings("rawtypes")
			List<VendorExtension> vendorExtensions = new ArrayList<VendorExtension>();
			ObjectVendorExtension requestParameters = new ObjectVendorExtension("x-ms-notification-content");
			requestParameters.addProperty(new StringVendorExtension("description", "Details for Webhook"));

			ObjectVendorExtension schemaExtension = new ObjectVendorExtension("schema");
			schemaExtension.addProperty(new StringVendorExtension("$ref", "#/definitions/" + PetWebhookResponse.class.getSimpleName()));
			requestParameters.addProperty(schemaExtension);

			vendorExtensions.add(requestParameters);
			context.operationBuilder().extensions(vendorExtensions);
		}
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return SwaggerPluginSupport.pluginDoesApply(delimiter);
	}
}
