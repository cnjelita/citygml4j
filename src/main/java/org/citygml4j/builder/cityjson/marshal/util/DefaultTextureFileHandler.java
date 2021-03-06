/*
 * citygml4j - The Open Source Java API for CityGML
 * https://github.com/citygml4j
 * 
 * Copyright 2013-2017 Claus Nagel <claus.nagel@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.citygml4j.builder.cityjson.marshal.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultTextureFileHandler implements TextureFileHandler {

	@Override
	public String getImageFileName(String imageURI) {
		if (imageURI != null) {
			
			try {
				// remote URLs are not supported
				new URL(imageURI).toURI();
				return null;
			} catch (MalformedURLException | URISyntaxException e) {
				//
			}
			
			imageURI = imageURI.replace('\\', '/');
			Path fileName = Paths.get(imageURI).getFileName();
			if (fileName != null)
				return fileName.toString();
		}
		
		return null;
	}

}
