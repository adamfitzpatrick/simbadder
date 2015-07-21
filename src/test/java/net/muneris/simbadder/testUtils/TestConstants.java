package net.muneris.simbadder.testUtils;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

public class TestConstants {
	public static final String NAME = "name";
	public static final byte[] BYTES = new byte[] { (byte)0x05 };
	public static final String STRING = "this is the expected test string";
	public static final String SIMBAD_FORMATTING_STRING = "format object name \""
			+ "{oType: { %OTYPE(numeric: \"N\", short: \"S\", shorter: \"3\", verbose: \"V\") }}\"";
	public static final String SIMBAD_RESPONSE_STRING = "{oTypeList: [{ numeric: \""
			+ "15.10.07.00\", short: \"QSO_Candidate\", veryshort: \"Q?\", verbose: "
			+ "\"Possible Quasar\" }{ numeric: \"15.15.04.00\", short: \"QSO\", "
			+ "veryshort: \"QSO\", verbose: \"Quasar\" }{ numeric: \"15.15.04.00\", "
			+ "short: \"QSO\", veryshort: \"QSO\", verbose: \"Quasar\"}], oType: { numeric: "
			+ "\"15.15.04.00\", short: \"QSO\", veryshort: \"QSO\", verbose: \"Quasar\" }}";
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
}
