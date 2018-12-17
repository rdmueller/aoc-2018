"use strict";

class Worker {
  constructor() {
    this.step = null;
  }
  execute(step) {
    this.step = step;
    step.worker = this;
  }
  incrementTime() {
    if (!this.step) return;
    if (this.step.duration > 0) {
      this.step.duration--;
      if (this.step.duration === 0) {
        this.step.run();
        this.step.worker = null;
        this.step = null;
      }
    }
  }
  isIdle() {
    return !this.step;
  }
}
function parallelExecutionTime(steps, workerCount, baseTaskDuration) {
  steps.forEach((step, index) => {
    step.duration = step.id.charCodeAt(0) - 64 + baseTaskDuration;
  });
  const workers = [];
  for (let i = 0; i < workerCount; i++) {
    workers.push(new Worker());
  }
  let duration = 0;
  while (steps.some(step => step.canRun())) {
    const idleWorkers = workers.filter(worker => worker.isIdle());
    const readySteps = steps.filter(
      step => step.canRun() && step.worker === undefined
    );
    const maxI = Math.min(idleWorkers.length, readySteps.length);
    for (let i = 0; i < maxI; i++) {
      idleWorkers[i].execute(readySteps[i]);
    }
    workers.forEach(worker => worker.incrementTime());
    duration++;
  }
  return duration;
}

module.exports = {
  parallelExecutionTime
};
