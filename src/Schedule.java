interface Schedule
{
	public void createEventEntries();
	public boolean cleanUp();
	public boolean runScheduledActions();
}