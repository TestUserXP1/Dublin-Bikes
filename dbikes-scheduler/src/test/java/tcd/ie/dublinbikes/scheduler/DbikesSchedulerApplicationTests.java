package tcd.ie.dublinbikes.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tcd.ie.dublinbikes.scheduler.controller.SchedulerJobController;
import tcd.ie.dublinbikes.scheduler.entity.JobServerResponse;
import tcd.ie.dublinbikes.scheduler.util.JobServerResponseCode;

/**
 * 
 * @author arun
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DbikesSchedulerApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DbikesSchedulerApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
	SchedulerJobController controller;

	String jobName = null;
	Date jobScheduleTime = null;
	String cronExpression = null;

	@Before
	public void setUp() throws ParseException {
		jobName = "Test1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		jobScheduleTime = sdf.parse("2018/04/01 12:03");
		cronExpression = "0 0 */4 ? * *";
	}

	@Test
	public void test1_ScheduleByJobName() throws ParseException {

		JobServerResponse response = controller.scheduleByJobName(jobName, jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
	}

	@Test
	public void test2_ScheduleByJobName_nullJobName() throws ParseException {

		JobServerResponse response = controller.scheduleByJobName("", jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_NAME_NOT_PRESENT, response.getStatusCode());
		assertEquals(false, (Boolean) response.getData());

	}

	@Test
	public void test2_ScheduleByJobName_invalidCron() throws ParseException {

		JobServerResponse response = controller.scheduleByJobName(null, jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_NAME_NOT_PRESENT, response.getStatusCode());
		assertEquals(false, (Boolean) response.getData());
	}

	@Test
	public void test2_ScheduleByJobName_DuplicateJob() throws ParseException {

		JobServerResponse response = controller.scheduleByJobName(jobName, jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_WITH_SAME_NAME_EXIST, response.getStatusCode());
		assertEquals(false, (Boolean) response.getData());
	}

	@Test
	public void test2_GetJobs() {

		JobServerResponse response = controller.getAllJobs();
		assertNotNull(response);
		assertEquals(1, ((List<Map<String, Object>>) response.getData()).size());
	}

	@Test
	public void test3_CheckJobName() throws ParseException {

		JobServerResponse response = controller.checkJobName(jobName);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
		assertEquals(true, (Boolean) response.getData());
	}

	@Test
	public void test3_CheckJobName_nullJobName() throws ParseException {

		JobServerResponse response = controller.checkJobName(null);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_NAME_NOT_PRESENT, response.getStatusCode());
		assertEquals(false, (Boolean) response.getData());
	}

	@Test
	public void test3_CheckJobName_emptyJobName() throws ParseException {

		JobServerResponse response = controller.checkJobName(null);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_NAME_NOT_PRESENT, response.getStatusCode());
		assertEquals(false, (Boolean) response.getData());
	}

	@Test
	public void test4_PauseByJobName() {

		JobServerResponse response = controller.pauseByJobName(jobName);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
		assertEquals(true, (Boolean) response.getData());
	}

	@Test
	public void test4_PauseByJobName_invalidJobName() {

		JobServerResponse response = controller.pauseByJobName("INVALID");
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_DOESNT_EXIST, response.getStatusCode());
		assertEquals(false, (Boolean) response.getData());
	}

	@Test
	public void test5_ResumeByJobName() {

		JobServerResponse response = controller.resumeByJobName(jobName);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
		assertEquals(true, (Boolean) response.getData());
	}

	@Test
	public void test6_UpdateJobByJobName() throws ParseException {
		String cronExpression = "0 0 */5 ? * *";

		JobServerResponse response = controller.updateJobByJobName(jobName, jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
		assertEquals(1, ((List<Map<String, Object>>) response.getData()).size());
	}

	@Test
	public void test6_UpdateJobByJobName_nullJobName() throws ParseException {
		String cronExpression = "0 0 */5 ? * *";

		JobServerResponse response = controller.updateJobByJobName(jobName, jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
		assertEquals(1, ((List<Map<String, Object>>) response.getData()).size());
	}

	@Test
	public void test6_UpdateJobByJobName_emptyJobName() throws ParseException {
		String cronExpression = "0 0 */5 ? * *";

		JobServerResponse response = controller.updateJobByJobName(jobName, jobScheduleTime, cronExpression);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
		assertEquals(1, ((List<Map<String, Object>>) response.getData()).size());
	}

	@Test
	public void test7_CheckForJobRunningStatus() throws ParseException {

		JobServerResponse response = controller.checkForJobRunningStatus(jobName);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
	}

	@Test
	public void test8_GetJobStateByJobName() throws ParseException {

		JobServerResponse response = controller.getJobStateByJobName(jobName);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
	}

	@Test
	public void test9_StartJobNow() {

		JobServerResponse response = controller.startJobNow(jobName);
		assertNotNull(response);
		assertEquals(JobServerResponseCode.SUCCESS, response.getStatusCode());
	}

	@Test
	public void test9_StartJobNow_invalidJobName() {

		JobServerResponse response = controller.startJobNow("INVALID");
		assertNotNull(response);
		assertEquals(JobServerResponseCode.JOB_DOESNT_EXIST, response.getStatusCode());
	}

	// @Test
	public void test11_UnscheduleByJobName() {

		// TODO Revisit the exception
		// controller.unscheduleByJobName(jobName);

	}

	@Test
	public void test12_DeleteByJobName() {

		JobServerResponse deleteResponse = controller.deleteByJobName(jobName);
		assertNotNull(deleteResponse);
	}

	@After
	public void tearDown() {
		// TODO
	}

}
