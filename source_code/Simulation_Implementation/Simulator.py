"""
Created on November 3, 2015

@author: rhondusmithwick
"""
from Queue import Queue
from atexit import register
from contextlib import closing
from itertools import repeat
from os import listdir
from threading import Thread
from time import time
from urllib2 import urlopen


class Machine:
    """
    Object for a Machine.
    :param: line of a file
    """
    urlBase = None

    def __init__(self, line):
        """
        Set instance attributes of this instance.
        :param: line of a file in the format (timeStamp, machineID, machineType, duration, netID, status)
        """
        line = [x.strip() for x in line.split(",")]
        line[0] = float(line[0])
        (self.timeStamp, self.machineID,
         self.machineType, self.duration,
         self.netID, self.status) = line

    def commit(self):
        """Access webservice."""
        url = "{}&machineID={}&duration={}&netID={}&status={}".format(Machine.urlBase, self.machineID, self.duration,
                                                                      self.netID, self.status)
        urlopener(url, self.toString())

    def toString(self):
        """
        Output for the Console.
        :return: a string that details the use of this machine.
        """
        baseString = ["At time", str(self.timeStamp), "attempted to make machine", self.machineID]
        if self.status == "busy":
            baseString.extend(("busy by", self.netID))
        elif self.status == "ooo":
            baseString.append("out of order")
        else:
            baseString.append("in order")
        return " ".join(baseString)


def createDic(path):
    """
    Parses through the directory and returns a dictionary
    that maps each timeStamp with a queue of the machines used at that time.
    Each line is in the format timeStamp, machineID, machineType, duration, netID, and status
    :param path: A directory name with a / as a string
    :return: a dictionary with timeStamp -> Queue(Machines)
    """
    d = {}
    files = listdir(path)
    for fileName in files:
        # Parse each of the files by turning each line into a Machine instance. 
        try:
            with open(path + fileName) as f:
                for line in f.readlines():
                    machine = Machine(line)
                    if machine.timeStamp not in d:
                        # We use a queue because we will need it for our threading later. 
                        d[machine.timeStamp] = Queue()
                    d[machine.timeStamp].put(machine)
            print fileName + " was parsed."
        except Exception as e:
            print "{} could not be parsed because of {}".format(fileName, e)
    return d


def access(machineQ):
    """
    Threading to access the web service quickly.
    :param machineQ: a queue of machine objects
    """
    numThreads = 20
    print "Number of Urls: {}".format(machineQ.qsize())
    print "Number of Threads: {}".format(numThreads)

    def task():
        # Each thread will keep pulling out a machine until there aren't anymore. 
        while True:
            machine = machineQ.get()
            machine.commit()
            machineQ.task_done()

    baseTime = time()
    for _ in repeat(None, numThreads):
        # Start a new thread for each of our workers.
        thread = Thread(target=task)
        thread.daemon = True
        thread.start()
    machineQ.join()
    print "Time taken was: {} \n".format(time() - baseTime)


def urlopener(url, outputString):
    """
    Open a url.
    :param url: url to open
    :param outputString: the string to output to the console
    """
    try:
        with closing(urlopen(url)) as _:
            pass
    except Exception as e:
        print "Could not access web service because of {}".format(e)
    # finally:
    #     ''' print the desired output; too much, but can uncomment'''
    #     print outputString


@register
def cleanUp():
    """
    Reset the MachineUse Table at start and end of program.
    """
    url = "{}&netID=cleanUp".format(Machine.urlBase)
    outputString = "Attempted to reset Machine table"
    urlopener(url, outputString)
    print outputString


def simulate(ip):
    """
    Simulate machine usage over 5 minutes.
    :param ip: the public IP of the web server
    """
    Machine.urlBase = "http://{}/service.php?operation=makeUpdate".format(ip)
    cleanUp()
    usageDic = createDic("sim/")  # input is path to the directory containg the files
    baseTime = time()
    relativeTime = 0.0
    while relativeTime < 300:
        if relativeTime in usageDic:
            print "Timestamp is: {}".format(relativeTime)
            access(usageDic[relativeTime])
        relativeTime = abs(time() - baseTime)


if __name__ == '__main__':
    simulate("--redacted---")
