package cv;

public class TestData {
  private final String testMsg;
  private final String name;

  public TestData(String testMsg, String name) {
    this.testMsg = testMsg;
    this.name = name;
  }

  public String getTestMsg() {
    return testMsg;
  }

  public String getName() {
    return name;
  }
}
