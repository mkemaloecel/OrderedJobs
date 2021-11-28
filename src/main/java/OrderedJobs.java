/**
 * @author kemal
 * @since 28.11.2021
 */
public interface OrderedJobs {
	void register(char job);
	void register(char job, char dependentJob);
	String[] sort() throws Exception;
}
