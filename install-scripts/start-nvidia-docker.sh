#!/bin/bash

echo "Starting"

sudo nvidia-docker-plugin &

echo "Verifying"

sudo nvidia-docker run --rm nvidia/cuda nvidia-smi
