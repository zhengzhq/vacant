package vacant.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class Test {

	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		repositoryService.createDeployment()
				.addClasspathResource("VacationRequest.bpmn20.xml").deploy();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("vacationRequest", variables);

		// Fetch all tasks for the management group
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateGroup("management").list();
		for (Task task : tasks) {
			System.out.println("Task available: " + task.getName());
		}

		Task task = tasks.get(0);

		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(task.getId(), taskVariables);

	}
}
