package org.citygml4j.util.lod;

import java.lang.reflect.Field;

import org.citygml4j.model.citygml.core.ImplicitRepresentationProperty;
import org.citygml4j.model.citygml.core.LodRepresentation;
import org.citygml4j.model.common.annotation.Lod;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.gml.base.AssociationByRepOrRef;
import org.citygml4j.model.gml.geometry.GeometryProperty;

public class LodRepresentationBuilder {

	private LodRepresentationBuilder() {
		// just to thwart instantiation
	}

	public static LodRepresentation buildRepresentation(Object object) {
		return buildFor(object, false);
	}
	
	@SuppressWarnings("unchecked")
	public static LodRepresentation buildFor(Object object, boolean getLodFromAttributeName) {
		LodRepresentation lods = new LodRepresentation();		

		for (Field field : object.getClass().getDeclaredFields()) {
			Class<?> type = field.getType();
			if (!GeometryProperty.class.isAssignableFrom(type) && !ImplicitRepresentationProperty.class.isAssignableFrom(type))
				continue;

			Integer lod = null;
			if (field.isAnnotationPresent(Lod.class)) {
				Lod lodProperty = field.getAnnotation(Lod.class);
				lod = lodProperty.value();
			} else if (getLodFromAttributeName) {
				String name = field.getName();
				if (name.length() > 3 && name.toLowerCase().startsWith("lod")) {
					try {
						lod = Integer.valueOf(name.substring(3, 4));
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}

			if (lod != null) {
				if (!field.isAccessible())
					field.setAccessible(true);

				try {
					Object property = field.get(object);
					lods.addRepresentation(lod, (AssociationByRepOrRef<? extends AbstractGML>)property);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					continue;
				}		
			}
		}

		return lods;
	}

}
