import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExtractWallpaper {
	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		if (!osName.equalsIgnoreCase("Windows 10")) {
			System.out.println("Only support Windows 10!");
			System.exit(0);
		}
		String userProfilePath = System.getenv("USERPROFILE");
		String localAppDataPath = System.getenv("LOCALAPPDATA");
		String childPath = "\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
		File sourceFile = new File(localAppDataPath + childPath);
		File destinationFile = new File(userProfilePath + "\\Desktop\\wallpapers-" + System.currentTimeMillis() + "\\");

		if (!sourceFile.isDirectory() || !sourceFile.exists()) {
			System.out.println("directory does not exist: " + sourceFile.getAbsolutePath());
			System.exit(0);
		}

		if (!destinationFile.exists()) {
			destinationFile.mkdirs();
		}

		File[] files = sourceFile.listFiles();
		for (File f : files) {
			if (!f.isDirectory() && f.length() > 204800) {
				String name = getFileName(f.getAbsolutePath());
				System.out.println("find wallpaper: " + name);
				try {
					Files.copy(f.toPath(), new File(destinationFile.getAbsolutePath() + "\\" + name + ".jpg").toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("save as: " + destinationFile.getPath());
		System.out.println("================================");
		System.out.println("Github: https://github.com/126g");
	}

	private static String getFileName(String s) {
		int pos = s.lastIndexOf("\\");
		String name = s.substring(pos + 1);
		return name;
	}
}
