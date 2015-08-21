package net.muneris.simbadder.testUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.muneris.simbadder.model.SimbadObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

public class TestConstants {
	public static final String NAME = "name";
	public static final byte[] BYTES = new byte[] { (byte)0x05 };
	public static final String STRING = "this is the expected test string";
	public static final String STRING2 = "this is another test string";
	public static final String SIMBAD_FORMATTING_STRING = "format object name \""
			+ "{mainOType: { %OTYPE(numeric: !`!N!`!, shortname: !`!S!`!, veryshortname: !`!3!`!, "
			+ "verbose: !`!V!`!) }}\"";
	public static final String SIMBAD_RESPONSE_STRING = "{oTypeList: [{ numeric: "
			+ "!`!15.10.07.00!`!, short: !`!QSO_Candidate!`!, veryshort: !`!Q?!`!, verbose: "
			+ "!`!Possible Quasar!`! }{ numeric: !`!15.15.04.00!`!, short: !`!QSO!`!, "
			+ "veryshort: !`!QSO!`!, verbose: !`!Quasar!`! }{ numeric: !`!15.15.04.00!`!, "
			+ "short: !`!QSO!`!, veryshort: !`!QSO!`!, verbose: !`!Quasar!`! }], oType: { numeric: "
			+ "!`!15.15.04.00!`!, short: !`!QSO!`!, veryshort: !`!QSO!`!, verbose: !`!Quasar!`! }}";
	public static final String SIMBADDER_RESPONSE_STRING = "[{\"mainOType\":null,\"coo\":null,"
			+ "\"distance\":10.0,\"parallax\":null,\"radialVelocity\":null,\"fluxList\":null,"
			+ "\"spectralType\":null,\"morphologicalType\":null,\"dimensions\":null,"
			+ "\"mainId\":null,\"idList\":null,\"bibCodeList\":null,\"measurements\":null,"
			+ "\"notes\":null,\"properMotion\":null,\"oTypeList\":null}]";
	public static final List<SimbadObject> SIMBAD_OBJECTS = new ArrayList<>();
	static {
		SimbadObject simbadObject = new SimbadObject();
		simbadObject.setDistance("10.0");
		SimbadObject simbadObject2 = new SimbadObject();
		simbadObject2.setDistance("100.0");
		SIMBAD_OBJECTS.add(simbadObject);
		SIMBAD_OBJECTS.add(simbadObject2);
	}
	public static final MockClientHttpRequest REQUEST = new MockClientHttpRequest();
	static {
		try {
			REQUEST.setURI(new URI("mock_uri"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	public static final ResponseEntity<String> RESPONSEENTITY =
			new ResponseEntity<>(STRING, HttpStatus.ACCEPTED);
	public static final MockClientHttpResponse CLIENTHTTPRESPONSE = new MockClientHttpResponse(BYTES, HttpStatus.ACCEPTED);
	public static final String SIMBAD_PARSE_ERROR = "::error::::\n\n[3] java.text.ParseException: error message: "
			+ "foo\n\nextra text";
	public static final String SIMBAD_FORMATTING_ERROR = "::error::::\n\n[3] 'unwanted string parts here' "
			+ "incorrect field in format: bad field";
}
