import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

/**
 * @author kemal
 * @since 28.11.2021
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderedJobsTest {
	private final static List<String> SORT_LIST = Arrays.asList("a","b","c");

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void sortATest() throws Exception {
		OrderedJobs orderedJobs =  new OrderedJobsImpl();
		orderedJobs.register('c');
		orderedJobs.register('b', 'a');
		orderedJobs.register('c', 'b');
		orderedJobs.register('c', 'a');
		List<String> list = Arrays.asList(orderedJobs.sort());
		System.out.println(list);
		Assert.assertEquals(list, SORT_LIST);
	}

	/**
	 * there is a circular dependency: b
	 * @throws Exception
	 */
	@Test
	public void sortFailureTest() throws Exception {
		OrderedJobs orderedJobs =  new OrderedJobsImpl();
		orderedJobs.register('c');
		orderedJobs.register('b', 'a');
		orderedJobs.register('c', 'b');
		orderedJobs.register('c', 'a');
		orderedJobs.register('a', 'b');
		exception.expect(Exception.class);
		List<String> list = Arrays.asList(orderedJobs.sort());
		System.out.println(list);

	}
}
