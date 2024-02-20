import iris.distroFinder.getPackageManager 
import iris.sysUpdate.sysUpdate

@main def main() =
    println(getPackageManager())
    sysUpdate()
    //if you read this you are a certified scalamancer