/*
 * Copyright (c) 2011-2014 by the original author(s).
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
package org.springframework.data.sequoiadb.core.convert;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.bson.BSONObject;
import org.junit.Test;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.geo.Shape;

import org.springframework.data.sequoiadb.core.convert.SequoiadbConverters.BigDecimalToStringConverter;
import org.springframework.data.sequoiadb.core.convert.SequoiadbConverters.StringToBigDecimalConverter;
import org.springframework.data.sequoiadb.core.geo.Sphere;

/**
 * Unit tests for {@link SequoiadbConverters}.
 * 


 */
public class SdbConvertersUnitTests {

	@Test
	public void convertsBigDecimalToStringAndBackCorrectly() {

		BigDecimal bigDecimal = BigDecimal.valueOf(254, 1);
		String value = BigDecimalToStringConverter.INSTANCE.convert(bigDecimal);
		assertThat(value, is("25.4"));

		BigDecimal reference = StringToBigDecimalConverter.INSTANCE.convert(value);
		assertThat(reference, is(bigDecimal));
	}

	/**
	 * @see DATA_JIRA-858
	 */
	@Test
	public void convertsBoxToDbObjectAndBackCorrectly() {

		Box box = new Box(new Point(1, 2), new Point(3, 4));

		BSONObject dbo = GeoConverters.BoxToDbObjectConverter.INSTANCE.convert(box);
		Shape shape = GeoConverters.DbObjectToBoxConverter.INSTANCE.convert(dbo);

		assertThat(shape, is((org.springframework.data.geo.Shape) box));
	}

	/**
	 * @see DATA_JIRA-858
	 */
	@Test
	public void convertsCircleToDbObjectAndBackCorrectly() {

		Circle circle = new Circle(new Point(1, 2), 3);

		BSONObject dbo = GeoConverters.CircleToDbObjectConverter.INSTANCE.convert(circle);
		Shape shape = GeoConverters.DbObjectToCircleConverter.INSTANCE.convert(dbo);

		assertThat(shape, is((org.springframework.data.geo.Shape) circle));
	}

	/**
	 * @see DATA_JIRA-858
	 */
	@Test
	public void convertsPolygonToDbObjectAndBackCorrectly() {

		Polygon polygon = new Polygon(new Point(1, 2), new Point(2, 3), new Point(3, 4), new Point(5, 6));

		BSONObject dbo = GeoConverters.PolygonToDbObjectConverter.INSTANCE.convert(polygon);
		Shape shape = GeoConverters.DbObjectToPolygonConverter.INSTANCE.convert(dbo);

		assertThat(shape, is((org.springframework.data.geo.Shape) polygon));
	}

	/**
	 * @see DATA_JIRA-858
	 */
	@Test
	public void convertsSphereToDbObjectAndBackCorrectly() {

		Sphere sphere = new Sphere(new Point(1, 2), 3);

		BSONObject dbo = GeoConverters.SphereToDbObjectConverter.INSTANCE.convert(sphere);
		org.springframework.data.geo.Shape shape = GeoConverters.DbObjectToSphereConverter.INSTANCE.convert(dbo);

		assertThat(shape, is((org.springframework.data.geo.Shape) sphere));
	}

	/**
	 * @see DATA_JIRA-858
	 */
	@Test
	public void convertsPointToListAndBackCorrectly() {

		Point point = new Point(1, 2);

		BSONObject dbo = GeoConverters.PointToDbObjectConverter.INSTANCE.convert(point);
		org.springframework.data.geo.Point converted = GeoConverters.DbObjectToPointConverter.INSTANCE.convert(dbo);

		assertThat(converted, is((org.springframework.data.geo.Point) point));
	}
}
