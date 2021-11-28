import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kemal
 * @since 28.11.2021
 */
public class OrderedJobsImpl implements OrderedJobs{

	private static Map<String, Set<String>> jobs = new HashMap<>();

	@Override
	public void register(char job) {
		String key = String.valueOf(job);

		jobs.put(key, getValue(key));
	}

	private Set<String> getValue(String key){
		Set<String> value = new HashSet<>();
		Set<String> existing = jobs.get(key);
		if(existing != null){
			value = existing;
		}
		return value;
	}

	@Override
	public void register(char job, char dependentJob) {
		String key = String.valueOf(job);
		Set<String> value = getValue(key);
		value.add(String.valueOf(dependentJob));
		jobs.put(key, value);
	}

	@Override
	public String[] sort() throws Exception {
		List<String> keyList = new ArrayList<>(jobs.keySet());
		String[] sortArray = keyList.toArray(new String[0]);
		Collection<Set<String>> sets = jobs.values();
		Set<String> values = sets.stream()
			.flatMap(Collection::stream)
			.collect(Collectors.toSet());

		String circularDependency = checkCircularDependencies(values);
		if(circularDependency != null){
			throw new Exception("there is a circular dependency: " + circularDependency);
		}
		values.addAll(keyList);
		String[] valueArray = values.toArray(new String[0]);
		return valueArray;
	}

	private String checkCircularDependencies(Set<String> dependencies){

		for (String dependency: dependencies) {
			if(checkDependency(dependency)){
				Set<String> valueSet = jobs.get(dependency);
				for (String value: valueSet) {
					if(checkDependency(value)){
						if(jobs.get(value).contains(dependency)){
							return value;
						}
					}
				}
				System.out.println("dependency: " + dependency + ", values: "  + valueSet);
			}
		}
		return null;
	}

	private boolean checkDependency(String dependency){
		List<String> jobList = new ArrayList<>(jobs.keySet());
		if(jobList.contains(dependency)){
			return true;
		}
		return false;
	}

}
