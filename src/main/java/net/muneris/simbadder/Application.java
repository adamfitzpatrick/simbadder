/**
 * Copyright (c) 2015 Adam Fitzpatrick
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.muneris.simbadder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 
 * <p>Main entry point for Simbadder.</p>
 * 
 * <p>Simbadder seeks to provide access to a modernized verion of the data
 * provided by the SIMBAD database and its associated APIs at the University
 * of Strasbourg.  SIMBAD represents one of the most complete publicly available
 * databases of astronomical objects.  As of July 8, 2015, the database contains
 * nearly 8 million objects.  I am extremely grateful to the researchers at the
 * University of Strasbourg for maintaining this resource and making it publicly
 * accessible.</p>
 * 
 * <p>Currently, the database provides information in HTML, text and VOTable formats.
 * Considerable effort is required to coerce search results into a highly readable
 * and machine-friendly format like JSON, either as part of the search query or after
 * obtaining results.</p>
 * 
 * <p>This tool acts as a pass-through microservice, providing APIs with flexible and
 * friendly query formats, and returning results pre-formatted to JSON.</p>
 * 
 * <p>Thanks to Pivotal Software, Inc and their SpringIO team, and to the researchers,
 * operators and staff at CDS, Strasbourg, France.</p>
 * 
 * <a href="http://simbad.u-strasbg.fr/simbad">http://http://simbad.u-strasbg.fr/simbad/</a>
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 * @since 2015-07-08
 * 
 */

@SpringBootApplication
public class Application{
	/** Application main method. */
	public static void main(String[] args) {
        SpringApplication.run(Application.class);
	}
}
