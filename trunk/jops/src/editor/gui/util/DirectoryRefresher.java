package gui.util;

public class DirectoryRefresher extends Thread {

	private QuickFileBrowser browser;

	boolean refreshing = true;
	
	public DirectoryRefresher(QuickFileBrowser browser) {
		this.browser = browser;
		this.start();
	}
	
    @Override
	public void run() {
		while (refreshing) {
			try {
				sleep(QuickFileBrowser.REFRESH_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			browser.refreshDirectory();
		}
	}

	public boolean isRefreshing() {
		return refreshing;
	}

	public void setRefreshing(boolean alive) {
		this.refreshing = alive;
	}

}