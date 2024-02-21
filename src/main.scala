import iris.distroFinder.getPackageManager 
import iris.sysUpdate.sysUpdate
import iris.distroFinder.aptWho

@main def main() =
    println(getPackageManager())
    sysUpdate()
    aptWho()
    //if you read this you are a certified scalamancer