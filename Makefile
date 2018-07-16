HOSTNAME = ml.benjmann.net

default:
	cat Makefile

ansible:
	#ansible-playbook ansible.yml -i $(HOSTNAME), --private-key emr-gleenn.pem  --verbose --user glenn
	ansible-playbook ansible.yml -i $(HOSTNAME),  --private-key ~/.ssh/id_bens_server_rsa --verbose --user glenn
	#--limit @/Users/glenn/workspace/tensorflow/cortex-toy/ansible.retry

ssh:
	ssh ben

remote_setup:
	scp -i ~/.ssh/id_bens_server_rsa -F ~/.ssh/config -r ./project.clj ./src ./resources ./test glenn@ml.benjmann.net:
	ssh -F ~/.ssh/config -i ~/.ssh/id_bens_server_rsa glenn@ml.benjmann.net "lein deps"

remote_repl:
#	ssh -i ~/.ssh/id_bens_server_rsa -F ~/.ssh/config ben "lein repl :headless :port 54545 & echo $! > lein.pid"
	ssh -i ~/.ssh/id_bens_server_rsa -F ~/.ssh/config ben "lein repl :headless :port 54545"

#kill_remote_repl:
#	ssh ben "killall lein"

tunnel_repl:
	ssh -L 54545:localhost:54545 glenn@ben -N

tunnel_web:
	ssh -L 8091:localhost:8091 glenn@ben -N

open_web:
	open http://localhost:8091

create-goog-instance:
	gcloud beta compute --project=ml-playground-206507 instances create instance-1 --zone=us-west1-b --machine-type=n1-standard-2 --subnet=default --network-tier=PREMIUM --metadata=ssh-keys=glenn:ssh-rsa\ AAAAB3NzaC1yc2EAAAADAQABAAACAQCwVS0BABgILA9y/ARpM7BhHdLAatFDUn9Rf5aGa4WMrXcsuLEDPbRxS\+Dah9RAQv0/P2qIZKyPSomj0wZVMbvogAUkISPGcqODhFC611N/yyvV7VRpyxN0DryHXYMfyixlv3p4WEik4b\+LXWD2KobjDwyZ7MJkqQWjfL8gG1P88gB0bk1oZNpXIMe89\+1pIWcmU4lkN6vi65qiek8SeYaCTRiLCM\+BmKpHFjyS1dVfPbcir/eNhds29jxdObcO8CbR0zUMNkNUWyI1iIgO6j/ODq\+\+G37aAEHany99EycVw80muEV5\+3okqe88alHdhcnNBwbjDzm5jOB8yFSgFd2L5shuYGnnWYsxqu3VDPO/bsTWGavuuhSoeaUbuPazh23Br1fT8rlOa6O4SN5\+4w4i3SL0IOfEphoA3HXErpGELZT\+uB0rwytgQ4ApFOYtXauLada\+cYWPqQjH1Z1IMhxCpG7FvCIxbT51Hq5paJeVlu3BJldyOtsdpUC7H/7X5BZBWNSJtkOe8Nv099JfT8xUpqrixxCirDP0ODIyLAwY7SexIGtaqpdDTMOl/6E3Tjp/KkcEEd2cYtKJo5zukO2DOoixcHMjYQjoQ5ndNH5H8Wl4PAo8/hErADmU83p1s8cWPCaLSotjlmSlWYtOf68OG1C0z6wp20ot0HrA2vVGlQ==\ glenn@umi.local --no-restart-on-failure --maintenance-policy=TERMINATE --preemptible --service-account=25786268718-compute@developer.gserviceaccount.com --scopes=https://www.googleapis.com/auth/cloud-platform --accelerator=type=nvidia-tesla-k80,count=1 --tags=http-server,https-server --image=ubuntu-1604-xenial-v20180627 --image-project=ubuntu-os-cloud --boot-disk-size=50GB --boot-disk-type=pd-standard --boot-disk-device-name=instance-1
    gcloud compute --project=ml-playground-206507 firewall-rules create default-allow-http --direction=INGRESS --priority=1000 --network=default --action=ALLOW --rules=tcp:80 --source-ranges=0.0.0.0/0 --target-tags=http-server
    gcloud compute --project=ml-playground-206507 firewall-rules create default-allow-https --direction=INGRESS --priority=1000 --network=default --action=ALLOW --rules=tcp:443 --source-ranges=0.0.0.0/0 --target-tags=https-server

install-goog-instance:
	scp -r scripts/* guppy
