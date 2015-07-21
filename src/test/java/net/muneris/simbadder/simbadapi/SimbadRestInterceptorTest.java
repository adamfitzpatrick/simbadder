package net.muneris.simbadder.simbadapi;

import static net.muneris.simbadder.testUtils.TestConstants.CLIENTHTTPRESPONSE;
import static net.muneris.simbadder.testUtils.TestConstants.REQUEST;
import static net.muneris.simbadder.testUtils.TestConstants.BYTES;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import net.muneris.simbadder.simbadapi.SimbadRestInterceptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

public class SimbadRestInterceptorTest {

	private SimbadRestInterceptor interceptor;
	
	@Before
	public void setUp() throws Exception {
		interceptor = new SimbadRestInterceptor();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIntercept() throws Exception {
		ClientHttpRequestExecution execution = createMock(ClientHttpRequestExecution.class);
		expect(execution.execute(REQUEST, BYTES)).andReturn(CLIENTHTTPRESPONSE);
		replay(execution);
		ClientHttpResponse response = interceptor.intercept(REQUEST, BYTES, execution);
		byte[] b = new byte[] {(byte) 0x05};
		response.getBody().read(b);
		assertEquals(BYTES[0], b[0]);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
	}

}
